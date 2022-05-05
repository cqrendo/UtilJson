import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '../../../components/form-buttons-bar.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-item.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';
import '../../generic/crud-view.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import '../../../components/query-buttons-bar.js';
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';

class GeneratedDetails extends ThemableMixin(PolymerElement) {
  static get template() {
    return html`
<style include="shared-styles">
      :host {
/*         height: 100%; */
        width: 100%;
/*         margin: 5px; */
        
      }

       vaadin-form-layout { 
       
        overflow: auto; 
       } 
      :host(.custom-style) [part="input-field"] {
        border: 10px solid #ccc;
        background-color: #bbb;
      }
       :host([focused].custom-style) [part="input-field"] {
        border-color: #aaa;
      }      
      vaadin-text-field.big{
                width: var(--vaadin-text-field-default-width, 20em);
                margin-left: 5px;
      }
      vaadin-text-field.medium{
                width: var(--vaadin-text-field-default-width, 10em);
                margin-left: 5px;
            }
      vaadin-text-field.verySmall{
            width: var(--vaadin-text-field-default-width, 4em);  
/*  				width : 4em;  */
 				margin-left: 5px;
             }  
      vaadin-text-field.small{
                width: var(--vaadin-text-field-default-width, 8em);
                margin-left: 5px;
            }  
      vaadin-date-picker {
      			margin-left: 5px;
      }  
      div.linCampos {
      margin-left: 20px;
      }     
/*       vaadin-form-item{ */
/*       	background-color: rgba(0, 0, 0, 0.05); */
/*             }                 */
/* /*       vaadin-text-field { */ */
/* /*   	border: 10px solid gray; */ */
/*   	height:"100px""; */
  	
/* 		} */
	clean{
		style:"padding-right:  0.75rem; margin-right: 0px;" 
	}
fieldset {
    color: #9a3261;
    font-size: smaller;
    border: 1px solid #9a3261;
    width:100%;
}

    </style>

`;
  }

  static get is() {
    return 'generated-details';
  }
      static get properties() {
        return {
            // Declare your properties here.
        };
    }

}
window.customElements.define(GeneratedDetails.is, GeneratedDetails);

