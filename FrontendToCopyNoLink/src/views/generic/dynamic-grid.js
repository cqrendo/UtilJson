import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '../../components/search-bar.js';
import '../../components/utils-mixin.js';
import '../../../styles/shared-styles.js';
import '@vaadin/vaadin-grid-pro/src/vaadin-grid-pro.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/flow-frontend/file-download-wrapper.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class DynamicGrid extends PolymerElement {
  static get template() {
    return html`
<style include="shared-styles">
      :host {
  /*    	height: 75%; */   
        display: flex; 
        flex-direction: column; 
    	
 /* 	overflow:hidden; */
      }
    </style>
<vaadin-horizontal-layout> 
 <div id="divExporter"></div>
 <div id="itemButtons">
  <vaadin-button id="newRow" theme="icon">
   <iron-icon icon="lumo:plus"></iron-icon>
  </vaadin-button>
  <vaadin-button id="deleteRow" theme="icon">
   <iron-icon icon="lumo:cross"></iron-icon>
  </vaadin-button>
 </div>
</vaadin-horizontal-layout>
<vaadin-grid-pro id="grid" column-reordering-allowed=""></vaadin-grid-pro>
`;
  }

  static get is() {
    return 'dynamic-grid';
  }
  _showHideQuery() {

var elmnt = this.parentElement.querySelector("#divQuery") ; // <- How do i get this component that is in Js that contains this
var vheight = elmnt.offsetHeight;
alert(" H -> " +vheight);
var c;
if (vheight == 0)
	{	
	this.$.divQuery.style.setProperty('display', 'block');
	vheight = elmnt.offsetHeight+60;
	c = 'calc(100% - '+vheight+'px)';
	}
else
	{
	c = 'calc(100% - 30px)';
	this.$.divQuery.style.setProperty('display', 'none');	
	}
this.$.querySplitGrid.style.setProperty('height', c);
 }
 // document.getElementById('querySplitGrid').style.setProperty('height', c);

}
window.customElements.define(DynamicGrid.is, DynamicGrid);

