import falcon
from falcon import HTTPError

Error_text = {
    0: ['Unknown Error',
        falcon.HTTP_500],
    1: ['SQL Error',
        falcon.HTTP_500],
    2: ['Json Required',
        falcon.HTTP_400],
    3: ['Empty Request Body',
        falcon.HTTP_400],
    4: ['Malformed JSON',
        falcon.HTTP_400],
}


class RError(HTTPError):
    def __init__(self, code=0):
        global Error_text
        self.code = code
        if self.code not in Error_text.keys():
            self.code = 0
        self.text = Error_text[self.code][0]
        self.http_code = Error_text[self.code][1]
        HTTPError.__init__(self, self.http_code, self.text, code=self.code)