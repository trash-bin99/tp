package seedu.fast.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.fast.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.fast.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.fast.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.fast.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.fast.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.fast.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.fast.testutil.TypicalPersons.getTypicalFast;

import org.junit.jupiter.api.Test;

import seedu.fast.model.Fast;
import seedu.fast.model.Model;
import seedu.fast.model.ModelManager;
import seedu.fast.model.UserPrefs;
import seedu.fast.model.person.Appointment;
import seedu.fast.model.person.Person;
import seedu.fast.testutil.PersonBuilder;

public class MarkAppointmentCommandTest {
    private static final String NO_APPOINTMENT_STUB = "No Appointment Scheduled Yet";
    private static final String NO_TIME_STUB = "";
    private static final String NO_VENUE_STUB = "";

    private final Model model = new ModelManager(getTypicalFast(), new UserPrefs());

    @Test
    public void equals() {
        final MarkAppointmentCommand standardCommand = new MarkAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(NO_APPOINTMENT_STUB, NO_TIME_STUB, NO_VENUE_STUB));

        // same values -> returns true
        MarkAppointmentCommand commandWithSameValues = new MarkAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(NO_APPOINTMENT_STUB, NO_TIME_STUB, NO_VENUE_STUB));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteAppointmentCommand(INDEX_SECOND_PERSON,
                new Appointment(NO_APPOINTMENT_STUB, NO_TIME_STUB, NO_VENUE_STUB))));
    }

    @Test
    public void execute_markAppointmentUnfilteredList_success() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(secondPerson)
                .withAppointment(NO_APPOINTMENT_STUB, NO_TIME_STUB, NO_VENUE_STUB).withAppointmentCount("1")
                .build();
        Appointment editedAppt = editedPerson.getAppointment();

        MarkAppointmentCommand appointmentCommand = new MarkAppointmentCommand(INDEX_SECOND_PERSON,
                new Appointment(editedAppt.getDate(), editedAppt.getTime(), editedAppt.getVenue()));

        String expectedMessage = String.format(MarkAppointmentCommand.MESSAGE_MARK_APPOINTMENT_SUCCESS,
                secondPerson.getName().fullName, secondPerson.getAppointment().getDate(),
                secondPerson.getAppointment().getTime(), secondPerson.getAppointment().getVenue());

        Model expectedModel = new ModelManager(new Fast(model.getFast()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);

        assertCommandSuccess(appointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markAppointmentUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAppointment(NO_APPOINTMENT_STUB, NO_TIME_STUB, NO_VENUE_STUB).build();
        Appointment editedAppt = editedPerson.getAppointment();

        MarkAppointmentCommand appointmentCommand = new MarkAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(editedAppt.getDate(), editedAppt.getTime(), editedAppt.getVenue()));

        String expectedMessage = String.format(MarkAppointmentCommand.MESSAGE_MARK_APPOINTMENT_FAILURE);

        assertCommandFailure(appointmentCommand, model, expectedMessage);
    }

    @Test
    public void execute_markAppointmentFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person secondPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(secondPerson)
                .withAppointment(NO_APPOINTMENT_STUB, NO_TIME_STUB, NO_VENUE_STUB).withAppointmentCount("1")
                .build();
        Appointment editedAppt = editedPerson.getAppointment();

        MarkAppointmentCommand appointmentCommand = new MarkAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(editedAppt.getDate(), editedAppt.getTime(), editedAppt.getVenue()));

        String expectedMessage = String.format(MarkAppointmentCommand.MESSAGE_MARK_APPOINTMENT_SUCCESS,
                secondPerson.getName().fullName, secondPerson.getAppointment().getDate(),
                secondPerson.getAppointment().getTime(), secondPerson.getAppointment().getVenue());

        Model expectedModel = new ModelManager(new Fast(model.getFast()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);

        assertCommandSuccess(appointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markAppointmentFilteredList_failure() {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson)
                .withAppointment(NO_APPOINTMENT_STUB, NO_TIME_STUB, NO_VENUE_STUB).build();
        Appointment editedAppt = editedPerson.getAppointment();

        MarkAppointmentCommand appointmentCommand = new MarkAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(editedAppt.getDate(), editedAppt.getTime(), editedAppt.getVenue()));

        String expectedMessage = String.format(MarkAppointmentCommand.MESSAGE_MARK_APPOINTMENT_FAILURE);

        assertCommandFailure(appointmentCommand, model, expectedMessage);
    }

}
