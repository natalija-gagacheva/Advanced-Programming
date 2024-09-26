package auditoriski.vezba5.GenerickoProgramiranje;

//mora da napomenime shto ke koristi DrawableInterface
public class Circle implements DrawableInterface<Circle>{

    @Override
    public Circle draw() {
        return this;
    }
}
