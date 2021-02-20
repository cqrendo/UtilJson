import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '../../../components/form-buttons-bar.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-item.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';
import '../../generic/crud-view.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '../../../components/query-buttons-bar.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';

class GeneratedQuery extends PolymerElement {
  static get template() {
    return html`
<style>
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
	    
       vaadin-form-item { 
       
    	flex: auto ;
/*	    overflow: auto;  */
/*	    --vaadin-form-item-label-width: 1em; */
/*        --vaadin-form-item-label-spacing: 1em; */
        --vaadin-form-item-row-spacing: 1.25em; 
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
		style="padding-right:  0.75rem; margin-right: 0px;" 
	}
    </style>
<h3 id="title"></h3>
<vaadin-horizontal-layout theme="" style="width: 100%; height: 100%;">
 <vaadin-form-layout id="form" style="flex-grow: 1;"></vaadin-form-layout>
 <!--  <vaadin-vertical-layout class="full"> -->
 <!--  <vaadin-form-item> -->
 <!-- </vaadin-form-item>  -->
 <!--  <br class="clean"> -->
 <!--  <vaadin-form-item> -->
 <!--  <vaadin-custom-field id="col25" label="Total" required error-message="Please fill in all fields"> -->
 <!--   <input type="decimal" id="input"> -->
 <!--   <select><option>Euro</option><option>Dollars</option><option>Pounds</option></select> -->
 <!--  </vaadin-custom-field> -->
 <!--  </vaadin-form-item> -->
 <!--  </vaadin-vertical-layout> -->
 <div class="linCampos" style="width: 5%; height: 10%;">
  <query-buttons-bar id="queryButtonsBar"></query-buttons-bar>
 </div>
</vaadin-horizontal-layout>
<!-- <vaadin-horizontal-layout theme="spacing" style="width: 100%; height: 100%;"></vaadin-horizontal-layout> -->
`;
  }

  static get is() {
    return 'generated-query';
  }
      static get properties() {
        return {
            // Declare your properties here.
        };
    }

}
window.customElements.define(GeneratedQuery.is, GeneratedQuery);

