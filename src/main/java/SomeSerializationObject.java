import java.io.Serializable;

public class SomeSerializationObject implements Serializable {

    private Person person;
    private ExternalizablePerson externalizablePerson;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ExternalizablePerson getExternalizablePerson() {
        return externalizablePerson;
    }

    public void setExternalizablePerson(ExternalizablePerson externalizablePerson) {
        this.externalizablePerson = externalizablePerson;
    }
}
