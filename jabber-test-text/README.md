# Test for authentication

This sample application is aimed to test sending simple text messages
with XMPP (Jabber) protocol

## Application arguments

| Option           | Argument                            | Required | Comment                                                                                 |
|------------------|-------------------------------------|----------|-----------------------------------------------------------------------------------------|
| `--user        ` | User ID in username@domain format.  | Yes      |                                                                                         |
| `--password    ` | User password.                      | Yes      |                                                                                         |
| `--domain      ` | XMPP domain.                        | No       | Without this option XMPP domain is set to domain part of user ID.                       |
| `--host        ` | XMPP server address.                | Yes      | Usually it is the same as XMPP domain.                                                  |
| `--insecure    ` | No.                                 | No       |                                                                                         |
| `--debug       ` | No.                                 | No       | Enables debug output of the application. It is written to `debug.log` file.             |
| `--enable-auth ` | Name of SASL mechanims.             | No       | Allows specified authentication mechanism.                                              |
| `--disable-auth` | Name of SASL mechanims.             | No       | Disallows specified authentication mechanism.                                           |

## Running the application

Run attached JAR file with command using result from login test by
disabling failed and enabling succeeded authentication mechanisms

```
java -jar jabber-test-text.jar \
    --user=user1@example.com \
    --password=user1_password \
    --host=example.com \
    --disable-auth=GSSAPI \
    --disable-auth=SCRAM-SHA-1-PLUS \
    --disable-auth=SCRAM-SHA-1 \
    --enable-auth=DIGEST-MD5 \
    --enable-auth=CRAM-MD5 \
    --enable-auth=PLAIN \
    --disable-auth=X-OAUTH2 \
    --disable-auth=EXTERNAL \
    --disable-auth=ANONYMOUS
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

On successful login application will ask for user ID to communicate

```
Login: OK
Enter user ID to send messages to:
```

Then _Hello_ message will be sent to the selected user.
The result should be `Sending message: OK`. Also, ensure that message
was delivered to the user.

## Troubleshooting

In case you faced some problems, try to run the application again with
`--debug` option and examine `debug.log` file contents.

 ```
 java -jar jabber-test-text.jar \
     --user=user1@example.com \
     --password=user1_password \
     --host=example.com \
     --debug
 ```

### Invalid certificate

If you have SSL/TLS errors, try adding `--insecure` option

```
 java -jar jabber-test-text.jar \
     --user=user1@example.com \
     --password=user1_password \
     --host=example.com \
     --insecure
```
