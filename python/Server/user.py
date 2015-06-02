__author__ = 'herbertqiao'
from utils import RUtils
from session import RMemorySessionStore
from database import RDataBase


class UserData:
    def __init__(self):
        self.username = ''
        self.email = ''
        self.status = 0
        self.id = 0
        self.token = ''
        self.ifLogin = False


class RUser:
    def __init__(self, token=''):
        self.sessions = RMemorySessionStore()
        self.utils = RUtils()
        self.database = RDataBase()
        self.data = None
        if token != '':
            self.restore_from_token(token)

    def restore_from_token(self, token):
        if self.sessions.contains(token):
            self.data = self.sessions.get(token)
            return True
        else:
            result = self.database.query('SELECT * FROM token WHERE token = %s AND expired_time > now()',
                                         (token,))
            if result:
                self.data = UserData()
                self.data.id = result[0]['user_id']
                self.data.ifLogin = True
                self.reload()
                return True
        self.data = UserData()
        return False

    def reload(self):
        result = self.database.query('SELECT * FROM users WHERE id = %s', (self.data.id,))
        if not result:
            return False
        self.data.username = result[0]['username']
        self.data.status = result[0]['status']
        self.data.email = result[0]['email']
        return True

    def login(self, username, password):
        if self.data.ifLogin:
            return True
        result = self.database.query('SELECT * FROm users