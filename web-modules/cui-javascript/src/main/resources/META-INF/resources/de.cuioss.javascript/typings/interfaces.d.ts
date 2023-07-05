
interface JsfAjax {
	request(id: string, obj:any, parameter:any):void;
	addOnError(callback:any):void;
	addOnEvent(callback:any):void;
}

interface JsfStatic {
	ajax:JsfAjax;
}

declare var jsf: JsfStatic;