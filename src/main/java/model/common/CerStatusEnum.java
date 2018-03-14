package model.common;

public enum CerStatusEnum {
	notAuthenticate("认证失败",0),
	authenticating("认证中",1), 
	authenticated("已认证",2);
	
	// 成员变量
    private String _name;
    private int _value;

    // 构造方法
    private CerStatusEnum(String name, int value) {
        this._name = name;
        this._value = value;
    }

    // 普通方法
    public static String getEnumName(Integer value) {
    	if(value==null)
    		value=0;
        for (CerStatusEnum c : CerStatusEnum.values()) {
            if (c.getValue() == value) {
                return c._name;
            }
        }
        return "未认证";
    }
    public static int getEnumValue(String name) {
    	if(name==null||name.isEmpty())
    		return 0;
        for (CerStatusEnum c : CerStatusEnum.values()) {
            if (c.getName().equals(name)) {
                return c._value;
            }
        }
        return 0;
    }

    // get set 方法
    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getValue() {
        return _value;
    }

    public void setIndex(int value) {
        this._value = value;
    }
	
}
