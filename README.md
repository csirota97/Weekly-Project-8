WEEK 8 - ListServer

In this project, I create a user interface that allows listserv administrators to modify listservs, as well as send messages out to them.

How To Use

Clone Repository Type the following command into your command line $ git clone https://github.com/csirota97/Weekly-Project-8.git

Import project into eclipse and run project. Note, main method is found in the Main.java file, in the "appication" package

On first run, see SETUP

Select the listserv a) From the home screen, click the "list" button. This will bring you to the active listserv. When the application is first opened, the default listserv is active. Although this is there, it is recommended not to use this one. b) Following the onscreen options, you can create a new list, change lists, add users, or delete users. A) If you elected to change to a different list, you must open the list. B) You will have the option to add/delete users. c) Once you are done modifying the desired list, click the "mail" button to return to the home screen.

Fill in the message subject, and body. a) If you would like to store drafts of the email, click the "save" button. This will prompt you to choose a location and file name. The message will be stored with a ".email" extension. b) You can open saved emails by clicking the "open" button and navigating to the email's saved location.

Once the email is ready to be sent, click the send button. The sender's email address will automatically be added to the end of the sendind list. Do not close the application until you have recieved the email. If you do not recieve a copy of the email, within a few minutes, an error has most likely occurred. See TROUBLESHOOTING

SETUP

Navigate to https://myaccount.google.com/lesssecureapps and allow less secure apps. a) In the upper right-hand corner of the browser window, make sure that the active account is the account for the address you intend on using in the application.

Click the "setup" button on the home screen.

In the pop-up dialog box, enter your full google email address, i.e. john.smith123@gmail.com, and click "ok".

In the new pop-up dialog box, enter the password associated with the email address and click "ok". Don't worry, we do not store that remotely. It is saved locally in /src/py/config.py

Clicking cancel on either of the dialog boxes will terminate the setup, but don't worry, you can always click the "setup" button to go back to the first dialog box.

TROUBLESHOOTING

Make sure you are using a valid google account

Make sure you have allowed less secure apps with the google account: https://myaccount.google.com/lesssecureapps

Make sure you have entered the email address and password correctly. This can be done by: a) recommended going through the setup process again b) navigate to /src/py/config.py and make sure the email address and password are correctly typed. If not, make any necessary corrections and save the file

NOTICE

This application only is currently configured to work with ****@gmail.com email addresses.

Although you do need to type in your password, it is stored locally. For user security, the passwords a NOT saved remotely on any servers.
