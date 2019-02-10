package edu.ntu.compilers.lab4.tokens;

/**
 * A token with variable content
 */
public class VariableToken extends Token
{
    public final String Attribute;

    VariableToken(String content, TokenName name) {
        super(name);
        Attribute = content;
    }

    @Override
    public String stringValue() {
        return Attribute;
    }

    @Override
    public int intValue() {
        try {
            return Integer.parseInt(Attribute);
        }
        catch (NumberFormatException e){
            throw new RuntimeException("Token value cannot be converted to int: " + this);
        }
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    public String toString() {
        return Name.name() + " (" + Attribute + ")";
    }
}
