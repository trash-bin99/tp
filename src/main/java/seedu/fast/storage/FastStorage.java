package seedu.fast.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.fast.commons.exceptions.DataConversionException;
import seedu.fast.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.fast.model.AddressBook}.
 */
public interface FastStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFastFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getFastFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFast(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveFast(ReadOnlyAddressBook)
     */
    void saveFast(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}
