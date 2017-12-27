# Test for discovering MUC service

This sample application is aimed to get information about Multi-user
chat service from XMPP (Jabber) server

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

## Running the application

Download zip archive from this [link](https://github.com/axibase/jabber-test/releases/download/v1.3/jabber-test.zip).
Extract archive contents to a new directory and navigate to it

```
mkdir jabber-test
unzip jabber-test.zip -d jabber-test
cd jabber-test
```

Run extracted jar file with command using result from [login test](../jabber-test-login/README.md) by
enabling one of succeeded authentication mechanisms

```
java -jar jabber-test-room.jar \
    --user=user1@example.com \
    --password=user1_password \
    --host=example.com \
    --port=5222 \
    --insecure \
    --auth=DIGEST-MD5
```

The example based on the results below

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

Application will request information about MUC service from the server

```
Login: OK
Request service items: OK
MUC services: conference.example.com
```

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
    --debug
```
