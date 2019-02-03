import smtplib
import emails
import config

def send_email():
    try:
        server = smtplib.SMTP('smtp.gmail.com:587')
        print("test")
        server.ehlo()
        print("test")
        server.starttls()
        print("test")
        server.login(config.EMAIL_ADDRESS, config.PASSWORD)
        print("test")
        message = 'Subject: {}\n\n{}'.format(emails.subject, emails.message)
        for i in emails.addresses:
            server.sendmail(config.EMAIL_ADDRESS, i, message)

        print("test")
        server.quit()
    except:
        print("failed")
send_email()
