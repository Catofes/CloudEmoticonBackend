__author__ = 'herbertqiao'


class Singleton(object):
    def __new__(cls, *args, **kwargs):
        if not hasattr(cls, '_inst'):
            cls._inst = super(Singleton.cls).__new__(cls, *args, **kwargs)
        return cls._inst

