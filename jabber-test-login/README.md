# Test for authentication

This sample application is aimed to test authentication on XMPP (Jabber)
server with different SASL mechanism

## Application arguments

| Option         | Argument                            | Required | Comment                                                                                 |
|----------------|-------------------------------------|----------|-----------------------------------------------------------------------------------------|
| `--user    `   | User ID in username@domain format.  | Yes      |                                                                                         |
| `--password`   | User password.                      | Yes      |                                                                                         |
| `--domain  `   | XMPP domain.                        | No       | Without this option XMPP domain is set to domain part of user ID.                       |
| `--host    `   | XMPP server address.                | Yes      | Usually it is the same as XMPP domain.                                                  |
| `--port    `   | XMPP server port.                   | No       | Set to 5222 by default.                                                                 |
| `--insecure`   | No.                                 | No       | Allows to connect to server with invalid/untrusted certificate                          |
| `--debug   `   | No.                                 | No       | Enables debug output of the application. It is written to `debug.log` file.             |


## Running the application

Download zip archive from the [link](https://github.com/axibase/jabber-test/releases/download/v1.6/jabber-test.zip).
Extract archive contents to a new directory and navigate to it

```
mkdir jabber-test
unzip jabber-test.zip -d jabber-test
cd jabber-test
```

Run extracted jar file with command

```
java -jar jabber-test-login.jar \
    --user=user1@example.com \
    --password=user1_password \
    --host=example.com \
    --port=5222 \
    --insecure
```

The application will try to authenticate on the server using different
authentication methods and print results

```
Will connect to host=example.com port=5222
password=**************
user=user1@example.com
domain=example.com
insecure enabled
debug.log enabled
Login with GSSAPI FAIL
Authentication with GSSAPI mechanism failed: Cannot perform login
Authentication with SCRAM-SHA-1-PLUS mechanism failed: Cannot perform login
Login with SCRAM-SHA-1-PLUS FAIL
Login with SCRAM-SHA-1 FAIL
Authentication with SCRAM-SHA-1 mechanism failed: Cannot perform login
Login with DIGEST-MD5 OK
Login with CRAM-MD5 OK
Login with PLAIN OK
Login with X-OAUTH2 FAIL
Authentication with X-OAUTH2 mechanism failed: Cannot perform login
Login with EXTERNAL FAIL
Authentication with EXTERNAL mechanism failed: Cannot perform login
Authentication with ANONYMOUS mechanism failed: Cannot perform login
Login with ANONYMOUS FAIL
```

## Troubleshooting

In case you faced some problems, try to run the application again with
`--debug` option and examine `debug.log` file contents.

```
java -jar jabber-test-login.jar \
    --user=user1@example.com \
    --password=user1_password \
    --host=example.com \
    --port=5222 \
    --insecure \
    --debug
```
