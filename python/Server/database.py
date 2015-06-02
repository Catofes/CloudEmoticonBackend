import MySQLdb
from error import RError
from DBUtils.PooledDB import PooledDB
from singleton import Singleton
from MySQLdb import cursors


class RDataBaseConnection:
    def __init__(self, db):
        self.db = db
        self.cursor = self.db.cursor()

    def execute(self, sql, param):
        try:
            self.cursor.execute("INSERT into input_params VALUE (now(), %s, %s)", (str(sql), str(param)))
            result = self.cursor.execute(sql, param)
        except Exception as e:
            print "MySQL Error Execute [%s] %r" % (sql, param)
            self.db.close()
            raise RError(1)
        return result

    def query(self, sql, param):
        return self.execute(sql, param)

    def commit(self):
        self.db.commit()
        self.db.close()


class RDataBase(Singleton):
    def __init__(self, config):
        self._db_pool = PooledDB(MySQLdb,
                                 user=config.db_user,
                                 passwd=config.db_passwd,
                                 host=config.db_host,
                                 port=config.db_port,
                                 db=config.db_db,
                                 mincached=config.db_mincached,
                                 maxcached=config.db_maxcached,
                                 maxshared=config.db_maxshared,
                                 maxconnections=config.db_maxconnections,
                                 cursorclass=cursors.DictCursor
                                 )

    def execute(self, sql, param):
        try:
            db = self._db_pool.connection()
            cursor = db.cursor()
            cursor.execute("INSERT into input_params VALUE (now(), %s, %s)", (str(sql), str(param)))
            result = cursor.execute(sql, param)
        except Exception as e:
            print "MySQL Error Execute [%s] %r" % (sql, param)
            db.close()
            raise RError(1)
        db.commit()
        db.close()
        return result

    def query(self, sql, param):
        try:
            db = self._db_pool.connection()
            cursor = db.cursor()
            cursor.execute("INSERT into input_params VALUE (now(), %s, %s)", (str(sql), str(param)))
            result = cursor.execute(sql, param)
        except Exception as e:
            print "MySQL Error [%s] %r" % (sql, param)
            db.close()
            raise RError(1)
        result = cursor.fetchall()
        db.close()
        return result

    def begin(self):
        try:
            db = self._db_pool.connection()
        except Exception as e:
            print "MySQL Error when begin an execute."
            db.close()
            raise RError(1)
        return RDataBaseConnection(id)

    def commit(self, con):
        return con.commit()