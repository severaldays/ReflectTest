package reflect.severaldays.reflecttest;

/**
 * Created by LingJian·HE on 16/7/18.
 */
public class ReflectClass {

    private int intVariable;
    private String stringVariable;
    private InternalClass internalClass;

    public ReflectClass(int intVariable, String stringVariable) {
        this.intVariable = intVariable;
        this.stringVariable = stringVariable;
        internalClass = new InternalClass("变量");
    }

    private int getIntVariable() {
        return intVariable;
    }

    private void setIntVariable(int intVariable) {
        this.intVariable = intVariable;
    }

    private String getStringVariable() {
        return stringVariable;
    }

    private void setStringVariable(String stringVariable) {
        this.stringVariable = stringVariable;
    }

    private void setValues(int intVariable, String stringVariable){
        this.intVariable = intVariable;
        this.stringVariable = stringVariable;
    }

    private class InternalClass {

        private String fieldName;

        public InternalClass(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }
}
