# Test for text message (rooms)

This sample application is aimed to test sending simple text messages
with XMPP (Jabber) protocol

## Application arguments

| Option            | Argument                                          | Required | Comment                                                                     |
|-------------------|---------------------------------------------------|----------|-----------------------------------------------------------------------------|
| `--user         ` | User ID in username@domain format.                | Yes      |                                                                             |
| `--password     ` | User password.                                    | Yes      |                                                                             |
| `--domain       ` | XMPP domain.                                      | No       | Without this option XMPP domain is set to domain part of user ID.           |
| `--host         ` | XMPP server address.                              | Yes      | Usually it is the same as XMPP domain.                                      |
| `--port         ` | XMPP server port.                                 | No       | Set to 5222 by default.                                                     |
| `--insecure     ` | No.                                               | No       | Allows to connect to server with invalid/untrusted certificate              |
| `--debug        ` | No.                                               | No       | Enables debug output of the application. It is written to `debug.log` file. |
| `--auth         ` | Name of SASL mechanim.                            | No       | Use the specified authentication mechanism.                                 |
| `--to           ` | Room ID in roomname@service format.               | Yes      | Room ID to join                                                             |
| `--nick         ` | Nickname of the user in the room, without spaces. | Yes      |                                                                             |
| `--room-password` | Room password.                                    | No       |                                                                             |

## Running the application

Download zip archive from this [link](https://github.com/axibase/jabber-test/releases/download/v1.6/jabber-test.zip).
Extract archive contents to a new directory and navigate to it

```
mkdir jabber-test
unzip jabber-test.zip -d jabber-test
cd jabber-test
```

Run extracted jar file with command using result from [login test](../jabber-test-login/README.md) by
enabling one of succeeded authentication mechanisms. Also, ensure that
MUC service is available on domain part of the room ID (`conference.example.com`
in this example) using [item test](../jabber-test-item/README.md)

```
java -jar jabber-test-room.jar \
    --user=user1@example.com \
    --password=user1_password \
    --host=example.com \
    --port=5222 \
    --insecure \
    --auth=DIGEST-MD5 \
    --to=room@conference.example.com \
    --nick=MyName \
    --room-password=room_password
```

The example based on the results of login test below

```
Login with GSSAPI FAIL
Login with SCRAM-SHA-1-PLUS FAIL
Login with SCRAM-SHA-1 FAIL
Login with DIGEST-MD5 OK
Login with CRAM-MD5 OK
Login with PLAIN OK
Login with X-OAUTH2 FAIL
Login with EXTERNAL FAIL
Login with ANONYMOUS FAIL
```

Application will perform login and then send _Hello_ message
to the selected room. The result should be

```
Login: OK
Join room: OK
Sending message: OK
Leave room: OK
```

Also, ensure that message is delivered to the room members.

## Troubleshooting

In case you faced some problems, try to run the application again with
`--debug` option and examine `debug.log` file contents.

```
java -jar jabber-test-room.jar \
    --user=user1@example.com \
    --password=user1_password \
    --host=example.com \
    --port=5222 \
    --insecure \
    --auth=DIGEST-MD5 \
    --to=room@conference.example.com \
    --nick=MyName \
    --room-password=room_password \
    --debug
```
