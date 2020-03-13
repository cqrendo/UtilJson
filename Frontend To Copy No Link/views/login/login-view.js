import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import { GestureEventListeners } from '@polymer/polymer/lib/mixins/gesture-event-listeners.js';
import '@vaadin/vaadin-icons/vaadin-icons.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-password-field.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '../../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
class LoginView extends GestureEventListeners(PolymerElement) {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
        width: 100vw;
        height: 100vh;
        --app-primary-color: var(--lumo-primary-color);
        --app-secondary-color: var(--lumo-body-text-color);

        background-color: var(--lumo-shade-5pct);
        display: flex;
        flex-direction: column;
        color: var(--lumo-body-text-color);
        font-family: var(--lumo-font-family);

        justify-content: center;
      }

      .container {
        margin: 0 auto;
        box-shadow: 0 2px 5px 0 var(--lumo-shade-10pct);
        display: flex;
        flex-direction: column;
        width: 100%;
        height: 100%;
      }

      .info {
        background-image: url(../../../images/login-banner.jpg);
        background-size: cover;
        background-position-x: center;
        width: 100%;
        padding: var(--lumo-space-l);
      }

      .info__header {
        font-weight: 300;
        color: var(--lumo-base-color);
        margin-top: 2em;
        margin-bottom: 0;
      }

      .info__text {
        font-size: var(--lumo-font-size-s);
        color: var(--lumo-tint-70pct);
        font-weight: 500;
        margin: 0;
      }

      .login {
        padding: var(--lumo-space-l);
        background-color: var(--lumo-base-color);
        flex: 1;
      }

      .login__header {
        margin-top: 0;
        margin-bottom: 0;
      }

      .login__layout {
        display: flex;
        flex-direction: column;
      }

      .login__layout > * {
        width: 100%;
      }

      .login__button {
        margin-top: var(--lumo-space-m);
        width: 100%;
        max-width: 100%;
      }

      .error {
        display: flex;
        align-items: flex-start;
      }

      .error__icon {
        flex: 0 0 auto;
        fill: var(--lumo-error-color);
        margin-top: 1.3em;
        margin-right: var(--lumo-space-s);
      }

      .error__text {
        max-width: 35em;
      }

      @media (max-height: 450px) {
        .info__header {
          margin-top: 0;
        }

        .login__header {
          display: none;
        }
      }

      @media (min-width: 750px) and (min-height: 450px) {
        .container {
          display: block;
          border-radius: 4px;
          max-width: 700px;
          overflow: hidden;
          height: auto;
        }
      }

      @media (min-width: 550px) {
        .login__layout {
          flex-direction: row;
          align-items: flex-end;
        }

        .login__layout > *:not(:first-child) {
          margin-left: var(--lumo-space-m);
        }

        .login__button {
          max-width: 7em;
        }
      }

    </style> 
   <div class="container"> 
    <div class="info"> 
     <h1 class="info__header">tys</h1> 
     <p class="info__text"> admin@vaadin.com + admin <br> barista@vaadin.com + barista </p> 
    </div> 
    <iron-form class="login" id="form" allow-redirect=""> 
     <form method="post" action="login"> 
      <h2 class="login__header">Sign in</h2> 
      <div class="login__layout"> 
       <vaadin-text-field id="username" name="username" label="Email" autofocus="" required=""></vaadin-text-field> 
       <vaadin-password-field id="password" name="password" label="Password" required="" on-keydown="_onPasswordKeydown"></vaadin-password-field> 
       <vaadin-button class="login__button" on-tap="login" theme="primary">
         Sign In 
       </vaadin-button> 
      </div> 
      <template is="dom-if" if="[[error]]"> 
       <div class="error"> 
        <iron-icon icon="vaadin:exclamation-circle-o" class="error__icon"></iron-icon> 
        <p class="error__text"> The username and password you entered do not match our records. Please double-check and try again. </p> 
       </div> 
      </template> 
     </form> 
    </iron-form> 
   </div> 
`;
  }

  static get is() {
    return 'login-view';
  }

  static get properties() {
    return {
      error: {
        type: Boolean,
        value: false
      }
    };
  }

  connectedCallback() {
    super.connectedCallback();
    this._measureLoadPerformance();
  }

  // This method is used to measure the page load performance and can be safely removed
  // if there is no need for that.
  _measureLoadPerformance() {
    const banner = new Image();
    banner.onload = () => {
      window.performance.mark && window.performance.mark('bakery-page-loaded');
    };

    const info = this.shadowRoot.querySelector('.info');
    const cssBackgroundImage = window.getComputedStyle(info).getPropertyValue('background-image');
    const match = cssBackgroundImage.match(/url\("?(.+?)"?\)/i);
    if (match) {
      banner.src = match[1];
    }
  }

  login() {
    if (!this.$.username.invalid && !this.$.password.invalid) {
      this.$.form.submit();
    }
  }

  _onPasswordKeydown(event) {
    if (event.key === 'Enter' || event.keyCode == 13) {
      this.login();
    }
  }
}

window.customElements.define(LoginView.is, LoginView);

