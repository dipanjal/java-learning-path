package com.dipanjal.batch1.annotation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationScanner {

    private static final ConcurrentHashMap<String, Object> beanMap;
    static {
        beanMap = new ConcurrentHashMap<>();
    }

    private static void componentScan(final String packageName)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, DuplicateBeanNameException {
        List<File> dirs = null;
        try {
            dirs = getDirectoryListInPackage(packageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File directory : dirs) {
            findClasses(directory, packageName);
        }
    }

    private static void findClasses(File directory, String packageName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DuplicateBeanNameException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if(file.isDirectory()) {
                findClasses(file, packageName+"."+file.getName());
            }
            if (file.getName().endsWith(".class")) {
                String fullyQualifyingClassName = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> aClass = Class.forName(fullyQualifyingClassName);
                registerAsBean(aClass);
            }
        }
    }

    private static void registerAsBean(Class<?> beanClass) throws InstantiationException, IllegalAccessException, DuplicateBeanNameException {
        String beanName = null;
        if (beanClass.isAnnotationPresent(MyController.class))
            beanName = throwIfDuplicateBean(beanClass.getAnnotation(MyController.class).value());
        else if (beanClass.isAnnotationPresent(MyService.class))
            beanName = throwIfDuplicateBean(beanClass.getAnnotation(MyService.class).value());

        if(beanName != null)
            beanMap.put(beanName, beanClass.newInstance());
    }

    private static String throwIfDuplicateBean(String beanName) throws DuplicateBeanNameException {
        if(beanMap.containsKey(beanName))
            throw new DuplicateBeanNameException("Already have a bean named "+ beanName);
        return beanName;
    }

    public static List<File> getDirectoryListInPackage(String packageName) throws IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        System.out.println(resources);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL dirUrl = resources.nextElement();
            dirs.add(new File(dirUrl.getFile()));
        }
        return dirs;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            componentScan("com.dipanjal.batch1");
            beanMap.forEach((key, instance) -> {
                try {
                    Method method = instance.getClass().getDeclaredMethod("print");
                    method.invoke(instance);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

            System.out.println(beanMap.get("userService"));
            System.out.println(beanMap.get("userController"));
            System.out.println(beanMap.get("homeController"));
        } catch (DuplicateBeanNameException e) {
            e.printStackTrace();
        }
    }
}
