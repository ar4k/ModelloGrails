'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Modulo principale by Ambrosini
 */
angular
  .module('sbAdminApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'ngAnimate',
    'angular-loading-bar',
    'hc.marked',
    'restangular',
    'nvd3'
  ])
  .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider) {
    
    $ocLazyLoadProvider.config({
	  <g:if env="development">debug:true,</g:if>
	  <g:if env="production">debug:false,</g:if>
      events:true,
    });

    $urlRouterProvider.otherwise('/dashboard/dashrossonet');

    $stateProvider
      .state('dashboard', {
        url:'/dashboard',
        templateUrl: "${resource(dir: 'admin', file: 'app/views/dashboard/main.html')}",
        resolve: {
            loadMyDirectives:function($ocLazyLoad){
                return $ocLazyLoad.load(
                {
                    name:'sbAdminApp',
                    files:[
                    "${resource(dir: 'admin', file: 'app/scripts/directives/header/header.js')}",
                    "${resource(dir: 'admin', file: 'app/scripts/directives/header/header-notification/header-notification.js')}",
                    "${resource(dir: 'admin', file: 'app/scripts/directives/sidebar/sidebar.js')}",
                    "${resource(dir: 'admin', file: 'app/scripts/directives/sidebar/sidebar-search/sidebar-search.js')}"
                    ]
                }),
                $ocLazyLoad.load(
                {
                   name:'toggle-switch',
                   files:["${resource(dir: 'bower_components', file: 'angular-toggle-switch/angular-toggle-switch.min.js')}",
                          "${resource(dir: 'bower_components', file: 'angular-toggle-switch/angular-toggle-switch.css')}"
                      ]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngAnimate',
                  files:["${resource(dir: 'bower_components', file: 'angular-animate/angular-animate.js')}"]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngCookies',
                  files:["${resource(dir: 'bower_components', file: 'angular-cookies/angular-cookies.js')}"]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngSanitize',
                  files:["${resource(dir: 'bower_components', file: 'angular-sanitize/angular-sanitize.js')}"]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngTouch',
                  files:["${resource(dir: 'bower_components', file: 'angular-touch/angular-touch.js')}"]
                })
            }
        }
    })
 
      .state('dashboard.api',{
        url:'/apiAr4k',
        controller: 'ApiAr4kCtrl',
        templateUrl:"${resource(dir: 'admin', file: 'apiAr4k')}",
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              "js!${resource(dir: 'admin', file: 'apiAr4kCtrl')}"
              ]
            })
          }
        }
      })
      
      .state('dashboard.console',{
        url:'/console',
        controller: 'ConsoleCtrl',
        templateUrl:"${resource(dir: 'admin', file: 'console')}",
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              "js!${resource(dir: 'admin', file: 'consoleCtrl')}"
              ]
            })
          }
        }
      })
 
      .state('dashboard.utenti',{
        url:'/utenti',
        controller: 'UtentiCtrl',
        templateUrl:"${resource(dir: 'admin', file: 'utenti')}",
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              "js!${resource(dir: 'admin', file: 'utentiCtrl')}"
              ]
            })
          }
        }
      })
      
     .state('dashboard.dashrossonet',{
        url:'/dashrossonet',
        controller: 'DashRossonetCtrl',
        templateUrl:"${resource(dir: 'admin', file: 'dashrossonet')}",
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'sbAdminApp',
              files:[
              "js!${resource(dir: 'admin', file: 'dashrossonetCtrl')}"
              ]
            })
          }
        }
      })
      
  }])
.controller('taskCtrl', function($scope,$http,$filter,$window) {
	$scope.pannelloMain=false;
	$scope.focusMain='';
	
    $scope.aggiornaDaMessaggio = function() {
    	$http.get("${createLink(controller:'admin',action:'listaProcessi',absolute:'true')}")
    		.success(function (response) {
    			$scope.processi = response.processi;
    			$scope.contoProcessi = response.conto;
    			}); 
  		};
  		
});
    
