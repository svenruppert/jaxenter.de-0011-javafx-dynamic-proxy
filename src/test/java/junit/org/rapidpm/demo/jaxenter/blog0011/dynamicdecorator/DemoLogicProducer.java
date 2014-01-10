/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package junit.org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator;


import org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator.DynamicObjectAdapterFactory;
import org.rapidpm.demo.jaxenter.blog0011.dynamicdecorator.ManagedInstanceCreator;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Created by Sven Ruppert on 09.01.14.
 */
public class DemoLogicProducer {

    @Inject Instance<DynamicObjectAdapterFactory> dynamicObjectAdapterFactoryInstance;

    @Inject Context context;

    @Produces @DynamicDecoratorTest
    public DemoLogic create(ManagedInstanceCreator instanceCreator){
        final DemoLogic demoLogic = instanceCreator.activateCDI(new DemoLogic() {});

        final DynamicObjectAdapterFactory dynamicObjectAdapterFactory = dynamicObjectAdapterFactoryInstance.get();

        final Object adapter;
        if (context.original){
            adapter = new Object();
        } else {
            adapter = instanceCreator.activateCDI(new DemoLogicAdapter_A());
        }

        return dynamicObjectAdapterFactory.adapt(demoLogic, DemoLogic.class, adapter);
    }
}
