__author__ = 'herbertqiao'

import falcon
import middleware
from wsgiref import simple_server


app = falcon.API(middleware=[
    middleware.RequireJSON(),
    middleware.JSONTranslator()
]
)

if __name__ == '__main__':
    httpd = simple_server.make_server('127.0.0.1', 8000, app)
    httpd.serve_forever()
