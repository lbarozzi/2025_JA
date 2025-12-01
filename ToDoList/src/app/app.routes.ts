import { Routes } from '@angular/router';
import { About } from './about/about';
import { App } from './app';
import { Listmanager  } from './listmanager/listmanager';

export const routes: Routes = [
   { path: "about", component: About },
   { path:"", component: Listmanager},
   { path:"foo", outlet:"bar", component: App }
];
