package ru.otus.atm.container;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.atm.util.TestUtil;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyBoxTest {
    private static final int EXPECTED_BALANCE = 6600;

    @Test
    @DisplayName("Check that put() creates the Money correctly")
    public void putNotesTest() {
        var moneyBox = MoneyBox.builder(1).put5000(0).put1000(0).put500(0).put100(0).build();
        var balanceEmptyBox = moneyBox.getBalance();

        assertThat(balanceEmptyBox).isEqualTo(0);

        var money = Money.builder().put5000(1).put1000(1).put500(1).put100(1).build();
        var actualBalanceAfterAdd = moneyBox.putNotes(money.getNumberOfNotes());
        var actualNotes = moneyBox.getNumberOfNotes();
        var expectedNotes = TestUtil.createBanknotes(1, 1, 1, 1);

        assertThat(actualBalanceAfterAdd).isEqualTo(EXPECTED_BALANCE);
        assertThat(actualNotes).containsExactlyInAnyOrderEntriesOf(expectedNotes);
    }

    @Test
    @DisplayName("Check that get() extracts banknotes correctly")
    public void extractNotesTest() {
        var moneyBox = MoneyBox.builder(1).put5000(1).put1000(1).put500(1).put100(1).build();
        var currentMoneyBoxBalance = moneyBox.getBalance();

        assertThat(currentMoneyBoxBalance).isEqualTo(EXPECTED_BALANCE);

        var request = TestUtil.createBanknotes(1, 1, 1, 1);
        var actualMoney = moneyBox.extractNotes(request);
        var actualMoneyBoxBalanceAfterExtracting = moneyBox.getBalance();

        var actualNumberOfNotesAfterExtracting = moneyBox.getNumberOfNotes();
        var expectedNumberOfNotesAfterExtracting = Map.copyOf(
                TestUtil.createEmptyBox(1).getNumberOfNotes()
        );
        assertThat(actualMoney.getNumberOfNotes()).containsExactlyInAnyOrderEntriesOf(request);
        assertThat(actualMoneyBoxBalanceAfterExtracting).isEqualTo(0);
        assertThat(actualNumberOfNotesAfterExtracting)
                .containsExactlyInAnyOrderEntriesOf(expectedNumberOfNotesAfterExtracting);
    }
}
