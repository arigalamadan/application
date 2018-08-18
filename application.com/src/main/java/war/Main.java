/*
 * Copyright 2010 Proofpoint, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package war;

import com.google.inject.Injector;
import com.app.bootstrap.Bootstrap;
import com.app.discovery.client.Announcer;
import com.app.discovery.client.DiscoveryModule;
import com.app.event.client.HttpEventModule;
import com.app.jmx.JmxHttpModule;
import com.app.http.server.HttpServerModule;
import com.app.jaxrs.JaxrsModule;
import com.app.jmx.JmxModule;
import com.app.jmx.http.rpc.JmxHttpRpcModule;
import com.app.json.JsonModule;
import com.app.log.LogJmxModule;
import com.app.log.Logger;
import com.app.node.NodeModule;
import com.app.tracetoken.TraceTokenModule;
import org.weakref.jmx.guice.MBeanModule;

public class Main
{
    private final static Logger log = Logger.get(Main.class);

    public static void main(String[] args)
            throws Exception
    {
        Bootstrap app = new Bootstrap(
                new NodeModule(),
                new DiscoveryModule(),
                new HttpServerModule(),
                new JsonModule(),
                new JaxrsModule(),
                new MBeanModule(),
                new JmxModule(),
                new JmxHttpModule(),
                new JmxHttpRpcModule(),
                new LogJmxModule(),
                new HttpEventModule(),
                new TraceTokenModule(),
                new MainModule());

        try {
            Injector injector = app.strictConfig().initialize();
            injector.getInstance(Announcer.class).start();
        }
        catch (Throwable e) {
            log.error(e);
            System.exit(1);
        }
    }
}
