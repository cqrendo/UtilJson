import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import './buttons-bar.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
{
  class QueryButtonsBarElement extends PolymerElement {
    static get template() {
      return html`
   <vaadin-vertical-layout style="width: 50px; height: 100px;"> 
    <vaadin-button theme="icon" aria-label="Buscar" id="bSearch" title="Buscar"> 
     <iron-icon icon="lumo:search"></iron-icon> 
    </vaadin-button> 
    <vaadin-button theme="icon" aria-label="Limpiar" id="bCleanSearch" title="Limpiar campos"> 
     <iron-icon icon="lumo:cross"></iron-icon> 
    </vaadin-button>  
    <!--   <iron-icon icon="lumo:plus"></iron-icon> --> 
   </vaadin-vertical-layout> 
`;
    }

    static get is() {
      return 'query-buttons-bar';
    }
  }
  window.customElements.define(QueryButtonsBarElement.is, QueryButtonsBarElement);
}

