import smtplib
from urllib.request import urlopen
from datetime import date, timedelta

config_file = open("mail.yaml")

conf = {}
for l in config_file:
    key, value = l.split(':')
    conf[key] = value[2:-2]

next_jueves = list(filter(lambda d: d.weekday() == 3, [date.today() + timedelta(x) for x in range(7)]))[0].isoformat()

url = "http://" + conf["service"]
bringer = urlopen(url + "toca-plain/" + next_jueves)

fromaddr = conf["from"]
toaddrs  = conf["to"]
username = conf["username"]
password = conf["password"]

body = "Le toca traer una docena de facturas a \"" + bringer.readall().decode("utf-8") + "\"<br>Evidencia: " + url + "toca/" + next_jueves
headers = ["from: " + '"Facturas" ' + fromaddr,
           "subject: " + conf["subject"] + " - " + next_jueves,
           "to: " + toaddrs,
           "mime-version: 1.0",
           "content-type: text/html"]
headers = "\r\n".join(headers)

server = smtplib.SMTP(conf["host"], conf["port"])
server.starttls()
server.login(username, password)
server.sendmail(fromaddr, toaddrs, headers + "\r\n\r\n" + body)
server.quit()
