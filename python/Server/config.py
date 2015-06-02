__author__ = 'herbertqiao'
from singleton import Singleton


class RConfig(Singleton):
    def __init__(self):
        self.paddword_salt="KTKTWonderful"
        self.db_user = "CloudEmoticon"
        self.db_passwd="nRpLEZft8DYGDnq5"
        self.db_host = "127.0.0.1"
        self.db_port = 3306
        self.db_db = "CloudEmoticon"
        self.db_mincached = 5
        self.db_maxcached = 40
        self.db_maxshared = 40
        self.db_maxconnections = 40
        self.session_cache_size = 1000