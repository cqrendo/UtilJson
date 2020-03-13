import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '../../../components/form-buttons-bar.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
class DynamicForm2 extends PolymerElement {
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
    <vaadin-text-field id="col0" label="Product name" colspan="2"></vaadin-text-field> 
    <vaadin-text-field id="col1" label="Unit price" colspan="2"></vaadin-text-field> 
    <vaadin-text-field id="col2" label="texto" colspan="2"></vaadin-text-field> 
   </vaadin-form-layout> 
   <form-buttons-bar id="buttons"></form-buttons-bar> 
   <label>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</label> 
`;
  }

  static get is() {
    return 'dynamic-form2';
  }
  ready() {
    super.ready();
    this.$.form.addEventListener('change', e => {
      this.$.buttons.$.save.disabled = false;
    });
  }
}
window.customElements.define(DynamicForm2.is, DynamicForm2);

