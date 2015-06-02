__author__ = 'herbertqiao'
from repoze.lru import LRUCache
from singleton import Singleton


class RMemorySessionStore(Singleton):
    def __init__(self, config):
        self._cache = LRUCache(config.session_cache_size)

    def push(self, token_id, data):
        self._cache.put(token_id, data)

    def get(self, token):
        return self._cache.get(token, None)

    def remove(self, token_id):
        try:
            self._cache.put(token_id, None)
        except KeyError:
            pass

    def contains(self, session_id):
        return self._cache.get(session_id) is not None