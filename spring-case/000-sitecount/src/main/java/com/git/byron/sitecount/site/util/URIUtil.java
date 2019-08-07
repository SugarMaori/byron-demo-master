package com.git.byron.sitecount.site.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.net.URI;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.sitecount.site.util
 * @ClassName: URIUtil
 * @Description:
 * @Date: 2019/8/7 9:25
 * @Version: 1.0
 */
public class URIUtil {

    public static ImmutablePair</**host*/String, /**uri*/String> foramtUri(String uri) {
        URI u = URI.create(uri);
        String host = u.getHost();
        if (u.getPort() > 0 && u.getPort() != 80) {
            host = host + ":80";
        }

        String baseUri = u.getPath();
        if (u.getFragment() != null) {
            baseUri = baseUri + "#" + u.getFragment();
        }

        if (StringUtils.isNotBlank(baseUri)) {
            baseUri = host + baseUri;
        } else {
            baseUri = host;
        }

        return ImmutablePair.of(host, baseUri);
    }

}
