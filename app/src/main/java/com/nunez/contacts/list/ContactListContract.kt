package com.nunez.contacts.list

import com.nunez.contacts.editContact.EditContactActivity
import com.nunez.contacts.entities.Contact
import rx.Observable
import kotlin.reflect.KClass


interface ContactListContract {

    interface View {
        fun showContacts(contacts: List<Contact>)
        fun showContactDetails(id: String)
        fun showErrorMessage()
        fun showDeleteBottomSheet(id: String)
        fun goToActivity(activityClass: KClass<EditContactActivity>)
        fun goToActivity(id:String, activityClass: KClass<EditContactActivity>)
    }

    interface Presenter {
        fun requestContacts()
        fun contactCliked(id: String)
        fun addContactClicked()
        fun contactLongCliked(id: String)
        fun contactToDeleteClicked(id: String)
        fun showErrorMessage()
        fun goToAddContactActivity()
        fun goToEditContactActivity()
    }

    interface Interactor {
        fun getContacts(): Observable<List<Contact>>
        fun deleteContact(id: String)
        fun addFakeContacts(): List<Contact>
    }
}