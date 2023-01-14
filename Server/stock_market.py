import yfinance as yf
import pandas as pd
from datetime import datetime
import time

def get_chart(stock_id: str):
    stock = yf.Ticker(stock_id)
    history = stock.history(period="30d", interval="1d").to_dict()
    chart = {}
    for k, v in history.items():
        if k not in ["High", "Low", "Open", "Close"]:
            continue
        chart[k] = {}
        d = {}
        for ts, price in v.items():
            d[datetime.timestamp(ts)] = price
        d = dict(sorted(d.items()))
        for i, (ts, price) in enumerate(d.items()):
            chart[k][i] = price
    return chart

def update_stocks(db, verbose: bool=False):

    if verbose: 
        print("[Getting Changes]")
        i = 1

    stocks = {}

    for stock_data in db.collection("stocks").get():
        stock_info = yf.Ticker(stock_data.id)
        try:
            new_price = stock_info.info["regularMarketPrice"]
            new_change = round(new_price - stock_info.info["regularMarketPreviousClose"], 2)

            stocks[stock_data.id] = {"currentPrice": new_price,
                                    "change": new_change}
        except:
            if verbose:
                print(f"[{i}/100] [Failed]")
                i += 1
        
        if verbose:
            print(f"[{i}/100]")
            i += 1

    if verbose:
        print("[Updating Database]")
        i = 1

    for stock_id, changes in stocks.items():
        db.collection("stocks").document(stock_id).update(changes)

        if verbose:
            print(f"[{i}/100]")
            i += 1
    
    if verbose: print("[Finished]")

def create_stocks(db, verbose: bool=False):

    if verbose: print("[Loading Stocks]")

    stocks = []
    with open("stocks.txt", "r") as stocks_file:
        for stock in stocks_file.readlines():
            stocks.append(stock.replace("\n", ""))
    
    if verbose: print("[Uploading Stocks to Database]")

    for i, stock_id in enumerate(stocks):
        stock_info = yf.Ticker(stock_id)
        full_name = stock_info.info["shortName"]
        current_price = stock_info.info["regularMarketPrice"]
        change = round(current_price - stock_info.info["regularMarketPreviousClose"], 2)

        new_stock_data = {"fullName": full_name,
                          "currentPrice": current_price,
                          "change": change}

        new_stock = db.collection("stocks").document(stock_id)
        new_stock.set(new_stock_data)

        if verbose: print(f"[{i+1}/{len(stocks)}]")
    
    if verbose: print("[Finished]")

def update_stocks_thread(db):
    while True:
        start = time.time()
        update_stocks(db)
        end = time.time()
        print(f"[Updated Stocks - {end - start}]")
