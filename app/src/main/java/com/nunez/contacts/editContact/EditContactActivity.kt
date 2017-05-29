package com.nunez.contacts.editContact

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nunez.contacts.R
import com.nunez.contacts.common.inputText
import com.nunez.contacts.entities.Contact
import com.nunez.contacts.list.ContactsListActivity
import com.nunez.contacts.repository.ContactsRepository
import kotlinx.android.synthetic.main.edit_contact_layout.*

class EditContactActivity() : AppCompatActivity(), EditContactContract.View {

    companion object {
        const val EXTRA_CONTACT_ID = "contact_id"
    }

    lateinit var currentContact: Contact
    val interactor: EditContactContract.Interactor by lazy { EditContactInteractor(ContactsRepository()) }
    val presenter: EditContactContract.Presenter by lazy { EditContactPresenter(this, interactor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_contact_layout)

        supportActionBar?.title = getString(R.string.edit_contact_activity_title)

        val arguments = intent.extras
        arguments?.let {
            val id = it.getString(ContactsListActivity.EXTRA_CONTACT_ID)
            presenter.getContactDetails(id)
        }

        saveBtn.setOnClickListener { presenter.onSaveClicked() }
        cancelBtn.setOnClickListener { presenter.onCancelClicked() }

        birthdayInput.setOnClickListener { presenter.onDateClicked() }
        birthdayInput.setOnFocusChangeListener {
            v, hasFocus ->
            if (hasFocus) presenter.onDateClicked()
        }
    }

    override fun showContact(contact: Contact) {
        currentContact = contact
        with(contact) {
            firstNameInput.inputText(firstName)
            lastNameInput.inputText(lastName)
            phoneNumberInput.inputText(phoneNumber)
            birthdayInput.inputText(birthday)
            zipcodeInput.inputText(zipCode)
        }
    }

    override fun closeView() {
        finish()
    }

    override fun getCurrentContactDetails(): Contact {
        with(currentContact) {
            firstName = firstNameInput.text.toString()
            lastName = lastNameInput.text.toString()
            phoneNumber = phoneNumberInput.text.toString()
            birthday = birthdayInput.text.toString()
            zipCode = zipcodeInput.text.toString()
        }
        return currentContact
    }

    override fun showDatePicker() {
        DatePickerFragment({
            dateSelected ->
            birthdayInput.inputText(dateSelected)
        }).show(supportFragmentManager, "datepicker")
    }
}
