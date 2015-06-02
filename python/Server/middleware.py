__author__ = 'herbertqiao'

import json
from error import RError


class RequireJSON(object):
    def process_request(self, req, resp):
        if not req.client_accepts_json:
            raise RError(2)

        if req.method in ('POST', 'PUT'):
            if 'application/json' not in req.content_type:
                raise RError(2)


class JSONTranslator(object):
    def process_request(self, req, resp):
        # req.stream corresponds to the WSGI wsgi.input environ variable,
        # and allows you to read bytes from the request body.
        #
        # See also: PEP 3333
        if req.content_length in (None, 0):
            # Nothing to do
            return

        body = req.stream.read()
        if not body:
            raise RError(3)

        try:
            req.context['request'] = json.loads(body.decode('utf-8'))

        except (ValueError, UnicodeDecodeError):
            raise RError(4)

    def process_response(self, req, resp, resource):
        if 'result' not in req.context.keys():
            return

        resp.body = json.dumps(req.context['result'])
