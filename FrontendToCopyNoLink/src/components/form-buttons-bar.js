import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import './buttons-bar.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
{
  class FormButtonsBarElement extends PolymerElement {
    static get template() {
      return html`
   <buttons-bar> 
    <vaadin-button id="save" slot="left" theme="raised primary">
      Salvar 
    </vaadin-button> 
    <vaadin-button id="cancel" slot="left" theme="raised">
      Cancelar 
    </vaadin-button> 
    <vaadin-button id="print" slot="left" theme="raised">
      <iron-icon icon="vaadin:print" slot="prefix"></iron-icon>
      Imprimir 
    </vaadin-button> 
    <vaadin-button id="add" slot="right" theme="raised primary">
      Nuevo 
    </vaadin-button> 
    <vaadin-button id="delete" slot="right" theme="raised tertiary error">
      Eliminar 
    </vaadin-button> 
   </buttons-bar> 
`;
    }

    static get is() {
      return 'form-buttons-bar';
    }
  }
  window.customElements.define(FormButtonsBarElement.is, FormButtonsBarElement);
}

