package sde.diagram;

import sde.utils.Constants;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// I need throwing a checked exception from a lambda
@FunctionalInterface
interface CheckedBiFunction<T, U, R> {
    R apply(T t, U u) throws Exception;
}

public class Parser {

    static CheckedBiFunction<SequenceDiagram, String, String> titleParser = (sequenceDiagram, line) -> line;
    static CheckedBiFunction<SequenceDiagram, String, String> notesParser = (sequenceDiagram, line) -> line;
    static CheckedBiFunction<SequenceDiagram, String, Message> messageParser = (sequenceDiagram, line) -> {

        Message message = new Message();
        String[] values = line.split(Constants.DEFINITIONS_SEPARATOR_FOR_REGEX);

        setRows(values[0], message);
        if (values.length < 3) {
            throw new Exception("A message must contain at least a row number, an entity and a message.");
        }

        String[] entities = values[1].split(Constants.ENTITY_SEPARATOR);
        if (!sequenceDiagram.containsEntityId(entities[0])) {
            throw new Exception(String.format("From Entity ID [%s] not defined.", entities[0]));
        }
        message.setFrom(sequenceDiagram.getEntity(entities[0]));

        if (entities.length == 2) {
            if (!sequenceDiagram.containsEntityId(entities[1])) {
                throw new Exception(String.format("To Entity ID [%s] not defined.", entities[1]));
            }
            message.setTo(Optional.of(sequenceDiagram.getEntity(entities[1])));
        }
        else {
            message.setTo(Optional.empty());
        }

        String[] labels = values[2].split(Constants.RESPONSE_SEPARATOR);
        message.setLabel(labels[0]);
        if (labels.length == 2) {
            message.setHasImmediateResponse(true);
            message.setResponseLabel(Optional.ofNullable(labels[1]));
        }

        if (values.length == 4) {
            message.setOrientation(Message.Orientation.valueOf(values[3].toUpperCase()));
        }

        return message;
    };
    private static Pattern pattern = Pattern.compile(Constants.ENTITY_ID_REGEX);
    static CheckedBiFunction<SequenceDiagram, String, Entity> entityParser = (sequenceDiagram, line) -> {

        String[] values = line.split(Constants.DEFINITIONS_SEPARATOR_FOR_REGEX);
        Matcher matcher = pattern.matcher(values[0]);
        if (!matcher.matches()) {
            throw new Exception("An entity ID must start with a letter and must contain only letters and numbers.");
        }

        if (values.length == 1) {
            return new Entity(line, line, Entity.Type.GENERIC);
        }
        else if (values.length == 2) {
            return new Entity(values[0], values[1], Entity.Type.GENERIC);
        }
        else {
            return new Entity(values[0], values[1], Entity.Type.valueOf(values[2].toUpperCase()));
        }
    };

    public static SequenceDiagram parseDatafile(String diagram) throws Exception {

        SequenceDiagram sequenceDiagram = new SequenceDiagram();

        BufferedReader reader = new BufferedReader(new StringReader(diagram));
        String line = null;
        int rows = 0;
        DataSection currentDataSection = null;

        try {
            while (true) {

                line = reader.readLine();
                rows++;
                if (line == null) {
                    break;
                }
                line = line.trim();

                // if it's a comment or blank
                if (line.startsWith(Constants.COMMENT_LINE) || line.length() == 0) {
                    continue;
                }

                // it's a section definer
                if (line.startsWith(Constants.SECTION_DEFINER)) {
                    String sectionLabel = line.substring(1).trim().toUpperCase();
                    try {
                        currentDataSection = DataSection.valueOf(sectionLabel);
                    }
                    catch (Exception ex) {
                        throw new Exception(String.format("Not existing section type [%s] at line %d.", sectionLabel, rows));
                    }
                    continue;

                }
                // it's the content of any section
                else {

                    switch (currentDataSection) {
                        case TITLE:
                            sequenceDiagram.setTitle(titleParser.apply(sequenceDiagram, line));
                            break;
                        case ABSTRACT:
                            sequenceDiagram.setAbstractText(notesParser.apply(sequenceDiagram, line));
                            break;
                        case ENTITIES:
                            sequenceDiagram.addEntity(entityParser.apply(sequenceDiagram, line));
                            break;
                        case MESSAGES:
                            sequenceDiagram.addMessage(messageParser.apply(sequenceDiagram, line));
                            break;
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new Exception(String.format("An error has occurred parsing line %d: %s. Line: %s", rows, ex.getMessage(), line));
        }
        return sequenceDiagram;
    }

    private static void setRows(String rows, Message message) throws Exception {
        try {
            if (rows.contains(Constants.WORKING_THREAD_ROWS_SEPARATOR)) {
                String[] rowsEdges = rows.split(Constants.WORKING_THREAD_ROWS_SEPARATOR_REGEX);
                int rowStart = Integer.parseInt(rowsEdges[0]);
                message.setRow(rowStart);
                message.setDuration(Integer.parseInt(rowsEdges[1]) - rowStart);
                return;
            }
            int row = Integer.parseInt(rows);
            message.setRow(row);
            message.setDuration(0);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(String.format("The row [%s] is not a valid number", rows));
        }
    }
}
