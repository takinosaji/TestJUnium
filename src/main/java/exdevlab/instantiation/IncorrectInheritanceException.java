package exdevlab.instantiation;

public class IncorrectInheritanceException extends Exception {
    public IncorrectInheritanceException(String[] derivedTypeNames, String[] baseTypeNames){
        super(String.format("{0} doesn't implement  {1}", String.join(",", derivedTypeNames), String.join(",", baseTypeNames)));
    }
    public IncorrectInheritanceException(String[] derivedTypeNames, String[] baseTypeNames, Exception innerException){
        super(String.format("{0} doesn't implement  {1}", String.join(",", derivedTypeNames), String.join(",", baseTypeNames)), innerException);
    }
}
