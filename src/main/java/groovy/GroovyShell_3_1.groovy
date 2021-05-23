package groovy

def sayHello(Map title) {
    HashMap map2 = title."12"

    println "Hello, " + map2."567"
    println title."12"."567"
    title."677"."id" = "uik"
    println title."677"."id"
    "GroovyShell_3_1中的sayHello()方法的返回值"
    def binding = new Binding()
    def engine = new GroovyScriptEngine([tmpDir.toURI().toURL()] as URL[])



}

sayHello(title)
