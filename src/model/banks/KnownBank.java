package model.banks;

public enum KnownBank {
    MELLI("603799","melli"),
    SEPAH("589210","sepah"),
    SADERAT("603769","saderat"),
    KESHAVARZI("603770","keshavarzi"),
    MASKAN("628023","maskan"),
    EGHTESADE_NOVIN("627412","eghtesade novin"),
    PARSIAN("622106","parsian"),
    SARMAYEH("639607","sarmayeh"),
    DEY("502938","dey"),
    MELLAT("610433","mellat"),
    TEJARAT("627353","tejarat");

    private String code;
    private String name;

    KnownBank(String code,String name) {
            this.code = code;
            this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
