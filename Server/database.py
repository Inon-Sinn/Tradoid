import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import urllib.parse

from fastapi import FastAPI, Request
import uvicorn

from responses import *
from stock_market import *

from threading import Thread

cred = credentials.Certificate("tradoidata-firebase-adminsdk-brre4-1e4798c444.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

app = FastAPI()

# GET methods

@app.get("/")
async def root():
    return {"passed": True,"data": "connection established"}

@app.get("/get_user/{user_id}")
async def get_user(user_id: str):
    try:
        user_dict = db.collection("users").document(user_id).get().to_dict()
        user = User(user_id, user_dict["username"], user_dict["email"], user_dict["balance"])

        return Response(user).respond()
   
    except Exception:
        return Response().respond()

@app.get("/user_list/")
async def get_user_list():
    try:
        user_list = UserList()
        for user_data in db.collection("users").get():
            user_dict = user_data.to_dict()
            user = User(user_data.id, user_dict["username"], user_dict["email"], user_dict["balance"])
            user_list.add_user(user)

        return Response(user_list).respond()
   
    except Exception:
        return Response().respond()

@app.get("/banned_list/")
async def get_banned_list():
    try:
        banned_ids = []
        for banned in db.collection("banned").get():
            banned_ids.append(banned.id)

        banned_user_list = UserList()
        for user_data in db.collection("users").get():
            if user_data.id in banned_ids:
                user_dict = user_data.to_dict()
                banned_user = User(user_data.id, user_dict["username"], user_dict["email"], user_dict["balance"])
                banned_user_list.add_user(banned_user)

        return Response(banned_user_list).respond()
    
    except Exception:
        return Response().respond()

@app.get("/get_balance/{user_id}")
async def get_balance(user_id: str):
    try:
        balance = Balance(db.collection("users").document(user_id).get().to_dict()["balance"])
        return Response(balance).respond()
    except Exception:
        return Response().respond()

@app.get("/is_banned/{user_id}")
async def is_banned(user_id: str):
    try:
        banned_user= db.collection("banned").document(user_id).get()
        if banned_user.exists:
            banned = IsBanned(True, urllib.parse.quote(banned_user.to_dict()["reason"]))
            return Response(banned).respond()
        
        return Response(IsBanned()).respond()

    except Exception:
        return Response().respond()

@app.get("/stock_list/")
async def get_stock_list():
    try:
        stock_list = StockList()
        for stock_data in db.collection("stocks").get():
            stock_dict = stock_data.to_dict()
            stock = Stock(stock_data.id, urllib.parse.quote(stock_dict["fullName"]), stock_dict["currentPrice"], stock_dict["change"])
            stock_list.add_stock(stock)

        return Response(stock_list).respond()
   
    except Exception:
        return Response().respond()

@app.get("/bookmarked/{user_id}")
async def get_bookmarked(user_id: str):
    try:
        stock_list = StockList()
        user_fields = db.collection("users").document(user_id).get().to_dict()
        if "bookmarked" in user_fields:
            for stock_id in user_fields["bookmarked"]:
                stock_dict = db.collection("stocks").document(stock_id).get().to_dict()
                stock = Stock(stock_id, urllib.parse.quote(stock_dict["fullName"]), stock_dict["currentPrice"], stock_dict["change"])
                stock_list.add_stock(stock)
        
        return Response(stock_list).respond()

    except Exception:
        return Response().respond()

@app.get("/portfolio/{user_id}")
async def get_portfolio(user_id: str):
    try:
        stock_list = StockList()
        updated_portfolio = {}

        user = db.collection("users").document(user_id)
        user_fields = user.get().to_dict()
        if "portfolio" in user_fields:
            for stock_id, amount in user_fields["portfolio"].items():
                if amount != 0:
                    updated_portfolio[stock_id] = amount
                    stock_dict = db.collection("stocks").document(stock_id).get().to_dict()
                    stock = Stock(stock_id, urllib.parse.quote(stock_dict["fullName"]), stock_dict["currentPrice"], stock_dict["change"])
                    stock_list.add_stock(stock)
        
        user.update({"portfolio": updated_portfolio})
        
        return Response(stock_list).respond()

    except Exception:
        return Response().respond()

@app.get("/status/{user_id}")
async def get_status(user_id: str):
    try:
        user_dict = db.collection("users").document(user_id).get().to_dict()
        owned_list = Portfolio()

        if "portfolio" in user_dict:
            for stock_id, amount in user_dict["portfolio"].items():
                stock_dict = db.collection("stocks").document(stock_id).get().to_dict()
                stock = Stock(stock_id, urllib.parse.quote(stock_dict["fullName"]), stock_dict["currentPrice"], stock_dict["change"])
                owned_list.add_owned(Owned(stock, amount))
        
        return Response(owned_list).respond()

    except Exception:
        return Response().respond()

@app.get("/get_stock_chart/{stock_id}")
async def get_stock_chart(stock_id: str):
    try:
        chart_dict = get_chart(stock_id)
        chart = StockChart()
        for k, v in chart_dict.items():
            for i, p in v.items():
                price = Price(i, p)
                chart.add_price(k, price)
        
        return Response(chart).respond()

    except Exception:
        return Response().respond()

@app.get("/email_exists/{email}")
async def email_exists(email: str):
    try:
        for user in db.collection("users").get():
            user_dict = user.to_dict()
            if user_dict["email"] == email:
                return Response(Success(True)).respond()
        
        return Response(Success(False)).respond()
    
    except Exception:
        return Response().respond()

@app.get("/get_dates/{user_id}")
async def get_dates(user_id: str):
    try:
        date_created = db.collection("users").document(user_id).get().to_dict()["dateCreated"]
        last_seen = db.collection("users").document(user_id).get().to_dict()["lastSeen"]

        return Response(Dates(urllib.parse.quote(date_created), urllib.parse.quote(last_seen))).respond()

    except Exception:
        return Response().respond()

@app.get("/get_history/{user_id}")
async def get_history(user_id: str):
    try:
        history = db.collection("users").document(user_id).get().to_dict()["history"]

        while len(history) > 100:
            history = history[1:]

        history_encoded = [urllib.parse.quote(x) for x in history]

        return Response(History(history_encoded)).respond()
    
    except Exception:
        return Response().respond()

@app.get("/load_pfp/{user_id}")
async def get_pfp(user_id: str):
    try:
        pfp_path = db.collection("users").document(user_id).get().to_dict()["pfpPath"]
        return Response(PFP("\"" + pfp_path + "\"")).respond()

    except Exception:
        return Response().respond()

# POST methods

@app.post("/create_user/")
async def create_user_try(request: Request):
    try:
        data = await request.json()
        username = data["username"]
        email = data["email"]
        password = data["password"]
        date_created = data["dateCreated"]

        # 0 = available, 1 = email taken, 2 = username taken
        availability_status = 0
        
        for user in db.collection("users").get():
            if user.to_dict()["email"] == email:
                availability_status = 1
                break
            if user.to_dict()["username"] == username:
                availability_status = 2
                break

        if availability_status != 0:
            return Response(CreateUserTry(availability_status)).respond()
        
        for admin in db.collection("admins").get():
            if admin.to_dict()["email"] == email:
                availability_status = 1
                break
            if admin.to_dict()["username"] == username:
                availability_status = 2
                break
        
        if availability_status != 0:
            return Response(CreateUserTry(availability_status)).respond()
        
        new_user_data = {"username": username, 
                        "email": email, 
                        "password": password,
                        "balance": 0.0,
                        "dateCreated": date_created,
                        "lastSeen": date_created}
        new_user = db.collection("users").document()
        new_user.set(new_user_data)
        return Response(CreateUserTry(availability_status, new_user.id)).respond()

    except Exception:
        return Response().respond()

@app.post("/create_admin/")
async def create_admin_try(request: Request):
    try:
        data = await request.json()
        username = data["username"]
        email = data["email"]
        password = data["password"]

        # 0 = available, 1 = email taken, 2 = username taken
        availability_status = 0

        for user in db.collection("users").get():
            if user.to_dict()["email"] == email:
                availability_status = 1
                break
            if user.to_dict()["username"] == username:
                availability_status = 2
                break

        if availability_status != 0:
            return Response(CreateUserTry(availability_status)).respond()
        
        for admin in db.collection("admins").get():
            if admin.to_dict()["email"] == email:
                availability_status = 1
                break
            if admin.to_dict()["username"] == username:
                availability_status = 2
                break
        
        if availability_status != 0:
            return Response(CreateUserTry(availability_status)).respond()
        
        new_admin_data = {"username": username, 
                        "email": email, 
                        "password": password}
        new_admin = db.collection("admins").document()
        new_admin.set(new_admin_data)
        return Response(CreateUserTry(availability_status, new_admin.id)).respond()
    
    except Exception:
        return Response().respond()

@app.post("/log_in/")
async def log_in_try(request: Request):
    try:
        data = await request.json()
        email = data["email"]
        password = data["password"]

        for user in db.collection("users").get():
            user_dict = user.to_dict()
            if user_dict["email"] == email and user_dict["password"] == password:
                log_in = LogInTry("user", user.id)
                return Response(log_in).respond()
        
        for admin in db.collection("admins").get():
            admin_dict = admin.to_dict()
            if admin_dict["email"] == email and admin_dict["password"] == password:
                log_in = LogInTry("admin", admin.id)
                return Response(log_in).respond()
        
        return Response(LogInTry()).respond()
    
    except Exception:
        return Response().respond()

@app.post("/ban_user/")
async def ban_user(request: Request):
    try:
        data = await request.json()
        user_id = data["userId"]
        reason = data["reason"]

        db.collection("banned").document(user_id).set({"reason": reason})
        return Response(Success(True)).respond()
    except Exception:
        return Response().respond()

@app.post("/unban_user/")
async def unban_user(requset: Request):
    try:
        data = await requset.json()
        user_id = data["userId"]

        banned_user =  db.collection("banned").document(user_id)
        if banned_user.get().exists:
            banned_user.delete()
            return Response(Success(True)).respond()
    
    except Exception:
        return Response().respond()

@app.post("/update_balance/")
async def update_balance(request: Request):
    try:
        data = await request.json()
        user_id = data["userId"]
        amount = data["amount"]

        user = db.collection("users").document(user_id)
        user.update({"balance": amount})
        balance = db.collection("users").document(user_id).get().to_dict()["balance"]
        
        return Response(Balance(balance)).respond()
        
    except Exception:
        return Response().respond()

@app.post("/bookmark_stock/")
async def bookmark_stock(request: Request):
    try:
        data = await request.json()
        user_id = data["userId"]
        stock_id = data["stockId"]

        user = db.collection("users").document(user_id)

        if "bookmarked" in user.get().to_dict():
            if stock_id in user.get().to_dict()["bookmarked"]:
                user.update({"bookmarked": firestore.ArrayRemove([stock_id])})
                return Response(Success(False)).respond()

        db.collection("users").document(user_id).update({"bookmarked": firestore.ArrayUnion([stock_id])})

        return Response(Success(True)).respond()
        
    except Exception:
        return Response().respond()

@app.post("/buy/")
async def buy(request: Request):
    try:
        data = await request.json()
        user_id = data["userId"]
        stock_id = data["stockId"]
        price = data["price"]
        amount = data["amount"]

        user = db.collection("users").document(user_id)
        user_dict = user.get().to_dict()

        if "portfolio" not in user_dict:
            user.update({"portfolio": {}})
            portfolio = {}
        else:
            portfolio = user_dict["portfolio"]

        if user_dict["balance"] < price:
            return Response().respond()

        if stock_id in portfolio:
            portfolio[stock_id] += amount
        else:
            portfolio.update({stock_id: amount})

        user.update({"portfolio": portfolio})
        user.update({"balance": firestore.Increment(-1*price)})

        today = datetime.today()
        today_str = str(today.day) + "." + str(today.month) + "." + str(today.year)

        user.update({"history": firestore.ArrayUnion([stock_id + "," + today_str + "," + "bought $" + str(round(price, 2))])})

        return Response(Balance(user.get().to_dict()["balance"])).respond()
            
    except Exception:
        return Response().respond()
    
@app.post("/sell/")
async def sell(request: Request):
    try:
        data = await request.json()
        user_id = data["userId"]
        stock_id = data["stockId"]
        price = data["price"]
        amount = data["amount"]

        user = db.collection("users").document(user_id)
        user_dict = user.get().to_dict()

        if "portfolio" not in user_dict:
            return Response().respond()
        portfolio = user_dict["portfolio"]

        if stock_id not in portfolio or portfolio[stock_id] < amount:
            return Response().respond()

        portfolio[stock_id] -= amount

        user.update({"portfolio": portfolio})
        user.update({"balance": firestore.Increment(price)})

        today = datetime.today()
        today_str = str(today.day) + "." + str(today.month) + "." + str(today.year)

        user.update({"history": firestore.ArrayUnion([stock_id + "," + today_str + "," + "sold $" + str(round(price, 2))])})

        return Response(Balance(user.get().to_dict()["balance"])).respond()
        
    except Exception:
        return Response().respond()

@app.post("/reset_password/")
async def reset_password(request: Request):
    try:
        data = await request.json()
        email = data["email"]
        new_password = data["newPassword"]

        for user in db.collection("users").get():
            user_dict = user.to_dict()
            if user_dict["email"] == email:
                db.collection("users").document(user.id).update({"password": new_password})
                return Response(Success(True)).respond()
        
        return Response(Success(False)).respond()
        
    except Exception:
        return Response().respond()

@app.post("/update_last_seen/")
async def update_last_seen(requset: Request):
    try:
        data = await requset.json()
        user_id = data["userId"]
        last_seen = data["lastSeen"]
        
        db.collection("users").document(user_id).update({"lastSeen": last_seen})
    
    except Exception:
        return Response().respond()

@app.post("/set_pfp/")
async def set_pfp(request: Request):
    try:
        data = await request.json()
        user_id = data["userId"]
        pfp_path = data["pfpPath"]

        db.collection("users").document(user_id).update({"pfpPath": pfp_path})

        return Response(Success(True)).respond()

    except Exception:
        return Response().respond()

if __name__ == "__main__":
    # update_stocks(db, verbose=True)
    thread = Thread(target=update_stocks_thread, args=(db,))
    thread.start()
    uvicorn.run(app, host="0.0.0.0", port=8000)