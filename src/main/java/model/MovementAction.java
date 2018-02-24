package model;

public class MovementAction {

    private FieldId originPosition;
    private FieldId targetPosition;

    public MovementAction(FieldId originPosition, FieldId targetPosition){
        this.originPosition = originPosition;
        this.targetPosition = targetPosition;
    }


    public FieldId getOriginPosition() {
        return originPosition;
    }

    public FieldId getTargetPosition() {
        return targetPosition;
    }


    public String toString(){
        return String.format("%s-%s", this.originPosition, this.targetPosition);
    }
}
