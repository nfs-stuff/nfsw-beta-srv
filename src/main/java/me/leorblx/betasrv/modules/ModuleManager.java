package me.leorblx.betasrv.modules;

import me.leorblx.betasrv.modules.config.ConfigModule;
import me.leorblx.betasrv.modules.http.HttpModule;
import me.leorblx.betasrv.modules.xmpp.XmppModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleManager
{
    private static ModuleManager ourInstance = new ModuleManager();

    public static ModuleManager getInstance()
    {
        return ourInstance;
    }

    private final Map<Class<? extends Module>, Module> modules;

    private ModuleManager()
    {
        this.modules = new HashMap<>();

        add(HttpModule.class, new HttpModule());
        add(XmppModule.class, new XmppModule());
        add(ConfigModule.class, new ConfigModule());
    }

    /**
     * Register a module.
     *
     * @param module The module to register.
     */
    public void register(Module module)
    {
        modules.put(module.getClass(), module);
    }

    /**
     * Boot a module.
     *
     * @param modClass The class of the module to boot.
     */
    public void boot(Class<? extends Module> modClass)
    {
        if (!modules.containsKey(modClass))
            throw new IllegalArgumentException("Class '" + modClass.getCanonicalName() + "' is not registered as a module!");
        modules.get(modClass).boot();
    }

    private <T extends Module> void add(Class<T> moduleClass, T instance)
    {
        modules.put(moduleClass, instance);
    }

    /**
     * Un-register a module.
     *
     * @param modClass The class of the module to un-register.
     */
    public void unregister(Class<? extends Module> modClass)
    {
        if (modules.containsKey(modClass))
            modules.remove(modClass);
    }

    /**
     * Boot all of the modules.
     */
    public void bootAll()
    {
        List<Module> moduleList = modules.values()
                .stream()
                .sorted((m1, m2) -> m1.getClass().getName().compareToIgnoreCase(m2.getClass().getName()))
                .collect(Collectors.toList());

        moduleList.stream().map(Module::getClass).forEach(this::boot);
    }

    public Map<Class<? extends Module>, Module> getModules()
    {
        return modules;
    }
}
