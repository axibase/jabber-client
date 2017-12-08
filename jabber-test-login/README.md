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
| `--insecure`   | No.                                 | No       |                                                                                         |
| `--debug   `   | No.                                 | No       | Enables debug output of the application. It is written to `debug.log` file.             |


## Running the application

Run attached JAR file with command

```
java -jar jabber-test-login.jar \
    --user=user1@example.com \
    --password=user1_password \
    --host=example.com
```

The application will try to authenticate on the server using different
authentication methods and print results

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

## Troubleshooting

In case you faced some problems, try to run the application again with
`--debug` option and examine `debug.log` file contents.

 ```
 java -jar jabber-test-login.jar \
     --user=user1@example.com \
     --password=user1_password \
     --host=example.com \
     --debug
 ```

### Invalid certificate

If you have SSL/TLS errors, try adding `--insecure` option

```
 java -jar jabber-test-login.jar \
     --user=user1@example.com \
     --password=user1_password \
     --host=example.com \
     --insecure
```
