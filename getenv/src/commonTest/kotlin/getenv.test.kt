import io.getenv
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GetEnvTest {
    @Test
    fun getenv_returns_null_if_env_var_is_not_set() {
        assertNull(getenv("NON_EXISTING_ENV_VAR"))
    }

    @Test
    fun getenv_should_return_a_value_for_path_env_var() {
        val envVarName = "PATH"
        val envVarValue = getenv(envVarName)
        println("$envVarName=$envVarValue")
        assertNotNull(envVarValue)
    }
}
