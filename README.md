## Web Security


## Method Security
http://confluence.marcuschiu.com/display/NOT/Java+-+Spring+-+Security

There are two points we'd like to remind regarding method security:
- By default, Spring AOP proxying is used to apply method security – if a secured method A is called by another method within the same class, security in A is ignored altogether. This means method A will execute without any security checking. The same applies to private methods
- Spring SecurityContext is thread-bound – by default, the security context isn't propagated to child-threads. For more information, we can refer to Spring Security Context Propagation article