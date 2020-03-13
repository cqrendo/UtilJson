import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-password-field.js';
import '../../../components/form-buttons-bar.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
class UserForm extends PolymerElement {
  static get template() {
    return html`
   <style>
      :host {
        display: flex;
        flex-direction: column;
        flex: auto;
        height: 100%;
      }

      vaadin-form-layout {
        flex: auto;
        overflow: auto;
      }
    </style> 
   <vaadin-form-layout id="form"> 
    <h3 id="title"></h3> 
    <vaadin-text-field id="email" label="Email (login)" colspan="2"></vaadin-text-field> 
    <vaadin-text-field id="first" label="First Name"></vaadin-text-field> 
    <vaadin-text-field id="last" label="Last Name"></vaadin-text-field> 
    <vaadin-password-field id="password" label="Password" colspan="2"></vaadin-password-field> 
    <vaadin-combo-box id="role" label="Role" colspan="2"></vaadin-combo-box> 
   </vaadin-form-layout> 
   <form-buttons-bar id="buttons"></form-buttons-bar> 
`;
  }

  static get is() {
    return 'user-form';
  }

  ready() {
    super.ready();
    this.$.form.addEventListener('change', e => {
      this.$.buttons.$.save.disabled = false;
    });
  }
}

window.customElements.define(UserForm.is, UserForm);

