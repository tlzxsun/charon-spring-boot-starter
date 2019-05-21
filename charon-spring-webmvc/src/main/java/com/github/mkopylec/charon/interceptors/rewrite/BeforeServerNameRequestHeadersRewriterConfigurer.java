package com.github.mkopylec.charon.interceptors.rewrite;

import com.github.mkopylec.charon.interceptors.RequestForwardingInterceptorConfigurer;

public class BeforeServerNameRequestHeadersRewriterConfigurer extends RequestForwardingInterceptorConfigurer<BeforeServerNameRequestHeadersRewriter> {

    private BeforeServerNameRequestHeadersRewriterConfigurer() {
        super(new BeforeServerNameRequestHeadersRewriter());
    }

    public static BeforeServerNameRequestHeadersRewriterConfigurer beforeServerNameRequestHeadersRewriter() {
        return new BeforeServerNameRequestHeadersRewriterConfigurer();
    }
}
