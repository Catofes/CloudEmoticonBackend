__author__ = 'herbertqiao'
from singleton import Singleton
import random
import string

class RUtils(Singleton):
    @staticmethod
    def generate_code(length):
        if length < 1:
            return False
        return ''.join(random.sample(string.ascii_letters + string.digits, length))
