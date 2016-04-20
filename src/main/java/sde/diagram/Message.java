package sde.diagram;

import java.util.Optional;

public class Message {

    private Entity from;
    private Optional<Entity> to;
    private String label;
    private int row;
    private int duration;
    private boolean hasImmediateResponse;
    private Optional<String> responseLabel;
    private Orientation orientation;

    public Message() {
    }

    public Entity getFrom() {
        return from;
    }

    public void setFrom(Entity from) {
        this.from = from;
    }

    public Optional<Entity> getTo() {
        return to;
    }

    public void setTo(Optional<Entity> to) {
        this.to = to;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setHasImmediateResponse(boolean hasImmediateResponse) {
        this.hasImmediateResponse = hasImmediateResponse;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean hasImmediateResponse() {
        return hasImmediateResponse;
    }

    public String getResponseLabel() {
        return responseLabel.orElse("");
    }

    public void setResponseLabel(Optional<String> responseLabel) {
        this.responseLabel = responseLabel;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", label='" + label + '\'' +
                ", row=" + row +
                ", duration=" + duration +
                ", hasImmediateResponse=" + hasImmediateResponse +
                ", responseLabel=" + responseLabel +
                '}';
    }

    public enum Orientation {
        LEFT, RIGHT;

    }
}
