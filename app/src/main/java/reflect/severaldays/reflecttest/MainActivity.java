package reflect.severaldays.reflecttest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";
    private ReflectClass reflectClass;
    private Button btGetField;
    private Button btSetField;
    private Button methodWithParameters;
    private Button methodWithTwoParameters;
    private Button methodWithoutParameters;
    private Button internalMethod;
    private TextView internalMethodResult;
    private TextView txFieldResult;
    private TextView txMethodResult;
    private int count;
    private View.OnClickListener onFieldGetClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 反射获取ReflectClass中的 intVariable 变量
            Object result1 = getFieldValue(reflectClass, "intVariable");
            // 反射获取ReflectClass中的 stringVariable 变量
            Object result2 = getFieldValue(reflectClass, "stringVariable");
            txFieldResult.setText("result1 = " + result1 + ",\n result2 = " + result2);
        }
    };
    private View.OnClickListener onFieldSetClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 反射获取ReflectClass中的 intVariable 变量，并对 intVariable 重新赋值
            setFieldValue(reflectClass, "intVariable", 10 + count);
            // 反射获取ReflectClass中的 stringVariable 变量，并对 stringVariable 重新赋值
            setFieldValue(reflectClass, "stringVariable", "reflect count = " + count);
            count++;
        }
    };

    private View.OnClickListener onMethodWithParamsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 反射调用ReflectClass中的 setIntVariable(int intVariable) 方法
            Object result1 = setMethod(reflectClass, "setIntVariable", 10 + count);
            // 反射调用ReflectClass中的 setStringVariable(String stringVariable) 方法
            Object result2 = setMethod(reflectClass, "setStringVariable", "带1个参数方法 = " + count);
        }
    };
    private View.OnClickListener onMethodWithoutParamsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 反射调用ReflectClass中的 getIntVariable 方法
            Object result1 = getMethod(reflectClass, "getIntVariable");
            // 反射调用ReflectClass中的 getStringVariable 方法
            Object result2 = getMethod(reflectClass, "getStringVariable");
            txMethodResult.setText("result1 = " + result1 + ",\n result2 = " + result2);
            count++;
        }
    };
    private View.OnClickListener onMethodWithTwoParamsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 反射调用ReflectClass中的 setValues(int intVariable, String stringVariable) 方法
            Object result = setMethod(reflectClass, "setValues", 10 + count, "带2个参数方法 count " + count);
            count++;
        }
    };
    private View.OnClickListener onInternalMethodWithoutParamsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 反射获得ReflectClass中的internalClass对象，并调用internalClass对象的 getFieldName() 方法
            Object result1 = getInternalMethod(reflectClass, "internalClass", "getFieldName");
            internalMethodResult.setText("result1 = " + result1);
        }
    };
    private View.OnClickListener onInternalMethodWithParamsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 反射获得ReflectClass中的internalClass对象，并调用internalClass对象的 setFieldName(String fieldName) 方法
            setInternalMethod(reflectClass, "internalClass", "setFieldName", "变量 = " + count);
            count++;
        }
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        reflectClass = new ReflectClass(10, "reflect test");
        btGetField = (Button) findViewById(R.id.bt_get_field_value);
        btGetField.setOnClickListener(onFieldGetClickListener);
        btSetField = (Button) findViewById(R.id.bt_set_field_value);
        btSetField.setOnClickListener(onFieldSetClickListener);
        txFieldResult = (TextView) findViewById(R.id.reflect_filed_result);

        methodWithParameters = (Button) findViewById(R.id.method_with_parameters);
        methodWithParameters.setOnClickListener(onMethodWithParamsClickListener);
        methodWithoutParameters = (Button) findViewById(R.id.method_without_parameters);
        methodWithoutParameters.setOnClickListener(onMethodWithoutParamsClickListener);
        methodWithTwoParameters = (Button) findViewById(R.id.method_with_two_parameters);
        methodWithTwoParameters.setOnClickListener(onMethodWithTwoParamsClickListener);
        txMethodResult = (TextView) findViewById(R.id.reflect_method_result);

        internalMethod = (Button) findViewById(R.id.internal_method_with_parameters);
        internalMethod.setOnClickListener(onInternalMethodWithoutParamsClickListener);
        internalMethod = (Button) findViewById(R.id.internal_method_without_parameters);
        internalMethod.setOnClickListener(onInternalMethodWithParamsClickListener);
        internalMethodResult = (TextView) findViewById(R.id.reflect_internal_method_result);
    }

    // 通过反射获取变量值
    public Object getFieldValue(Object cls, String fieldName) {
        try {
            Class workerClass = Class.forName(cls.getClass().getName());
            Field declaredField = workerClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            Object value = declaredField.get(cls);
            Log.i(TAG, "getFieldName value" + value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "getFieldName Exception e" + e);
            return null;
        }
    }

    // 通过反射改变变量值
    public void setFieldValue(Object cls, String fieldName, Object value) {
        try {
            Class workerClass = Class.forName(cls.getClass().getName());
            Field declaredField = workerClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(cls, value);
            Log.i(TAG, "setFieldValue value" + value);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "setFieldValue Exception e" + e);
        }
    }

    // 通过反射调用无参构造方法
    private Object getMethod(Object cls, String methodName) {
        try {
            Class workerClass = Class.forName(cls.getClass().getName());
            Method declaredMethod = workerClass.getDeclaredMethod(methodName);
            declaredMethod.setAccessible(true);
            Object object = declaredMethod.invoke(cls);
            return object;
        } catch (Exception e) {
            return e;
        }
    }

    // 通过反射调用带参数参构造方法
    private Object setMethod(Object cls, String methodName, Object value) {
        try {
            Class workerClass = Class.forName(cls.getClass().getName());
            Class[] clsArray = new Class[] {String.class};
            if (value instanceof Integer) {
                clsArray = new Class[] {int.class};
            }
            Method declaredMethod = workerClass.getDeclaredMethod(methodName, clsArray);
            declaredMethod.setAccessible(true);
            Object object = declaredMethod.invoke(cls, value);
            return object;
        } catch (Exception e) {
            return e;
        }
    }

    // 通过反射调用带多个参数参构造方法
    //private void setValue(int intVariable, String stringVariable){ // 目标函数
    private Object setMethod(Object cls, String methodName, Object value1, Object value2) {
        try {
            Class workerClass = Class.forName(cls.getClass().getName());
            Class[] clsArray = new Class[] {int.class, String.class};
            Method declaredMethod = workerClass.getDeclaredMethod(methodName, clsArray);
            declaredMethod.setAccessible(true);
            Object object = declaredMethod.invoke(cls, value1, value2);
            return object;
        } catch (Exception e) {
            return e;
        }
    }

    // 通过反射获取类中变量的方法
    private Object getInternalMethod(Object cls, String fieldObjectName, String fieldObjectMethodName) {
        try {
            Class workerClass = Class.forName(cls.getClass().getName());
            Field field = workerClass.getDeclaredField(fieldObjectName);
            field.setAccessible(true);
            Object fieldObject = field.get(cls);
            Class fieldObjectClass = Class.forName(fieldObject.getClass().getName());
            Method fieldObjectMethod = fieldObjectClass.getDeclaredMethod(fieldObjectMethodName);
            fieldObjectMethod.setAccessible(true);
            Object object = fieldObjectMethod.invoke(fieldObject);
            return object;
        } catch (Exception e) {
            return e;
        }
    }

    private Object setInternalMethod(Object cls, String fieldObjectName, String fieldObjectMethodName, String value) {
        try {
            Class workerClass = Class.forName(cls.getClass().getName());
            Field field = workerClass.getDeclaredField(fieldObjectName);
            field.setAccessible(true);
            Object fieldObject = field.get(cls);
            Class fieldObjectClass = Class.forName(fieldObject.getClass().getName());
            Method fieldObjectMethod = fieldObjectClass.getDeclaredMethod(fieldObjectMethodName, new Class[]{String
                    .class});
            fieldObjectMethod.setAccessible(true);
            Object object = fieldObjectMethod.invoke(fieldObject, value);
            return object;
        } catch (Exception e) {
            return e;
        }
    }
}
