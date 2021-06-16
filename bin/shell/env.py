class Env:
    
    database = "test"
    
    @staticmethod
    def getEnvAsDictionary():
        return {
            "database": Env.database
        }