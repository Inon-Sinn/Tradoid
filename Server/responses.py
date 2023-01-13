class Response:
    def __init__(self, data=None) -> None:
        self.passed = False
        if data:
            self.passed = True
            self.data = data.__dict__
    
    def respond(self):
        return self.__dict__

class User(Response):
    def __init__(self, user_id: str, username: str, email: str, balance: float) -> None:
        self.userId = user_id
        self.username = username
        self.email = email
        self.balance = balance

class UserList(Response):
    def __init__(self) -> None:
        self.userList = []
    
    def add_user(self, user: User):
        self.userList.append(user.respond())

class Balance(Response):
    def __init__(self, balance: float) -> None:
        self.balance = balance

class IsBanned(Response):
    def __init__(self, is_banned: bool=False, reason: str=None) -> None:
        self.isBanned = is_banned
        self.reason = reason

class CreateUserTry(Response):
    def __init__(self, availability_status: int, user_id: str=None) -> None:
        self.availabilityStatus = availability_status
        self.userId = user_id

class LogInTry(Response):
    def __init__(self, type: str=None, user_id: str=None) -> None:
        self.type = type
        self.userId = user_id

class Success(Response):
    def __init__(self, success: bool=False) -> None:
        self.success = success

class Stock(Response):
    def __init__(self, stock_id: str, full_name: str, current_price: float, change: float) -> None:
        self.stockId = stock_id
        self.fullName = full_name
        self.currentPrice = current_price
        self.change = change

class StockList(Response):
    def __init__(self) -> None:
        self.stockList = []

    def add_stock(self, stock: Stock):
        self.stockList.append(stock.respond())

class Price(Response):
    def __init__(self, day: int, price: float) -> None:
        self.day = day
        self.price = price

class StockChart(Response):
    def __init__(self) -> None:
        self.high = []
        self.low = []
        self.open = []
        self.close = []
    
    def add_price(self, key: str, price: Price):
        if key == "High":
            self.high.append(price.respond())
        elif key == "Low":
            self.low.append(price.respond())
        elif key == "Open":
            self.open.append(price.respond())
        elif key == "Close":
            self.close.append(price.respond())

class Owned(Response):
    def __init__(self, stock: Stock, amount: float) -> None:
        self.stock = stock
        self.amount = amount

class Portfolio(Response):
    def __init__(self) -> None:
        self.ownedList = []
    
    def add_owned(self, owned: Owned):
        self.ownedList.append(owned.respond())

class Dates(Response):
    def __init__(self, date_created: str, last_seen: str) -> None:
        self.dateCreated = date_created
        self.lastSeen = last_seen

class History(Response):
    def __init__(self, history) -> None:
        self.history = history

class PFP(Response):
    def __init__(self, pfp_path: str) -> None:
        self.pfpPath = pfp_path